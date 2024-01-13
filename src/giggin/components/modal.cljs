(ns giggin.components.modal
  (:require [giggin.state :as state :refer [update-gig]]))

(defn modal []
  (let [{:keys [title price description]} @state/editing-giggin
        close-modal #(do
                       (reset! state/editing-giggin nil)
                       (reset! state/modal-opened false))
        submit #(do
                  (.preventDefault %)
                  (update-gig @state/editing-giggin)
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
      [:textarea.form__input {:id "description" :value description
                              :on-change #(reset! state/editing-giggin (assoc @state/editing-giggin :description (-> % .-target .-value)))}]]
     [:div.row.form-action
      [:button.btn {:on-click #(close-modal)}
       "Cancel"]
      [:button.btn.btn--primary {:type "submit"}
       "Save Changes"]]]]]))
