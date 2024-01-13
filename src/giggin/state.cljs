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
        [(keyword (str id))
            {:id (str id)
             :title title
             :artist artist
             :desc description
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