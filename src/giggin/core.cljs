(ns giggin.core
  (:require [reagent.core :as r]
            [giggin.components.header :refer [header]]
            [giggin.components.gigs :refer [gigs]]
            [giggin.components.orders :refer [orders]]
            [giggin.components.footer :refer [footer]]
            [giggin.components.modal :refer [modal]]
            [giggin.state :as state]))

(defn app
  []
  [:div.container
   [header]
   [gigs]
   [orders]
   [footer]
   (when @state/modal-opened [modal])
   ])

(defn ^:export main
  []
  (r/render
    [app]
    (.getElementById js/document "app")))
