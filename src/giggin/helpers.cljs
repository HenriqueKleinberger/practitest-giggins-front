(ns giggin.helpers)

(defn format-price
  [price]
  (str " $" (/ price 100)))