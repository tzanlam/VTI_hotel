import { url } from "./axios"

export const ReviewService = {
    fetchReviews(){
        return url.get("findReviews")
    },

    fetchReviewById(reviewId){
        return url.get(`findReviewById?reviewId=${reviewId}`)
    },

    createReview(reviewRequest){
        return url.post("createReview", reviewRequest)
    },

    updateReview(reviewId, reviewRequest){
        return url.put(`updateReview?reviewId=${reviewId}`, reviewRequest)
    }
}