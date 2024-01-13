(ns giggin.state
  (:require [reagent.core :as r]
             [cljs.core.async :refer [go]]
            [cljs.core.async.interop :refer-macros [<p!]]
            ))

(def modal-opened (r/atom false))
(def editing-giggin (r/atom nil))
(def orders (r/atom {}))
(def gigs (r/atom nil))
(def loading-state (r/atom false))

(defn fetch-gigs-success [gigs-data]
(into {}
  (map (fn [gig]
         (let [{:keys [id image title artist price description soldOut]} (js->clj gig :keywordize-keys true)]
        [id
            {:id id
             :title title
             :artist artist
             :description description
             :image image
             :price price
             :sold-out soldOut}]))
       gigs-data)))

(defn fetch-gigs []
  (reset! loading-state true)
  (let [url "https://localhost:44376/giggins"]
    (go
      (try
        (let [response (<p! (js/fetch url))
              result   (<p! (.json response))]
          (reset! gigs (fetch-gigs-success result)))
        (catch js/console.error err "Failed to fetch gigs" err))))
  (reset! loading-state false))

(defn update-gig [gig]
  (reset! loading-state true)
  (let [url "https://localhost:44376/giggins"
        id (:id gig)
        update-url (str url "/" id)]
    (go
      (try
        (<p! (js/fetch update-url
                       (clj->js {:method "PUT" :headers {:Content-Type "application/json"}
                        :body (js/JSON.stringify (clj->js gig))})))
        (fetch-gigs)
        (catch js/console.error err "Failed to fetch gigs" err))))
  (reset! loading-state false))