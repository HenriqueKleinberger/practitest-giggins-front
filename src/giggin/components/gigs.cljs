(ns giggin.components.gigs
  (:require [giggin.helpers :refer [format-price]]
            [giggin.state :as state :refer [fetch-gigs]]
            [reagent.core :as reagent]))

(defn gigs []
  (reagent/create-class
   {:component-did-mount #(fetch-gigs)
    :reagent-render (fn [] (let [add-to-order #(swap! state/orders update % inc)
                                 edit-gig #(do
                                             (reset! state/editing-giggin (get @state/gigs %))
                                             (reset! state/modal-opened true))]
                             [:main
                              [:div.gigs
                               (for [{:keys [id img title price desc]} (vals @state/gigs)]
                                 [:div.gig {:key id}
                                  [:img.gig__artwork {:src img :alt title}]
                                  [:div.gig__body
                                   [:div.gig__title
                                    [:div.btn.btn--primary.float--right.tooltip.button-action-margin
                                     {:data-tooltip "Edit Giggin" :on-click #(edit-gig id)}
                                     [:i.icon.edit-pencil]]
                                    [:div.btn.btn--primary.float--right.tooltip.button-action-margin
                                     {:data-tooltip "Add to order" :on-click #(add-to-order id)}
                                     [:i.icon.icon--plus]] title]
                                   [:p.gig__price (format-price price)]
                                   [:p.gig__desc desc]]])]]))}))
