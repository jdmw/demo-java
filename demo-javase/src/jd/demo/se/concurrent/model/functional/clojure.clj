(ns jd.demo.se.concurrent.model.functional.sum)

(defn recursive-sum [numbers]
  (if (empty? numbers)
    0
    (+ (first numbers) (recursive-sum (rest numbers)))
  )
)