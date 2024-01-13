(ns giggin.components.modal
  (:require [giggin.state :as state]))

(defn modal []
  (let [{:keys [id title price desc]} @state/editing-giggin
        close-modal #(do
                       (reset! state/editing-giggin nil)
                       (reset! state/modal-opened false))
        submit #(do
                  (.preventDefault %)
                  (swap! state/gigs
                         (fn [gigs]
                           (-> gigs
                               (assoc-in [id :desc] desc)
                               (assoc-in [id :price] price)
                               (assoc-in [id :title] title))))
                  (reset! state/editing-giggin nil)
                  (reset! state/modal-opened false))]
  [:div.modal-overlay
   [:div.modal-content
    [:button.btn.btn--clear {:on-click #(close-modal)} ]
    [:div.row [:h2.modal-title "Editing Giggin" ]]
    [:form {:on-submit #(submit %)}
     [:div.row
      [:div.field
       [:label.form__label {:for "title"} "Title"]
       [:input.form__input {:type "text" :id "title" :value title
                      :on-change #(reset! state/editing-giggin (assoc @state/editing-giggin :title (-> % .-target .-value)))}]]
      [:div.field
       [:label.form__label {:for "price"} "Price"]
       [:input.form__input {:type "number" :id "price" :value price
                      :on-change #(reset! state/editing-giggin (assoc @state/editing-giggin :price (-> % .-target .-value)))}]]]
     [:div.field
      [:label.form__label {:for "description"} "Description"]
      [:textarea.form__input {:id "description" :value desc
                              :on-change #(reset! state/editing-giggin (assoc @state/editing-giggin :desc (-> % .-target .-value)))}]]
     [:div.row.form-action
      [:button.btn {:on-click #(close-modal)}
       "Cancel"]
      [:button.btn.btn--primary {:type "submit"}
       "Save Changes"]]]]]))
