import { url } from "./axios"

export const FastBookingService = {
    fetchFastBookings(){
        return url.get("getFastBookings")
    },

    fetchFastBookingById(fastBookingId){
        return url.get(`getFastBookingById?fastBookingId=${fastBookingId}`)
    },

    fetchFastBookingByName(fastBookingFullName){
        return url.get(`getFastBookingByName?fastBookingFullName=${fastBookingFullName}`)
    },

    fetchFastBookingByPhoneNumber(fastBookingPhoneNumber){
        return url.get(`getFastBookingByPhoneNumber?fastBookingPhoneNumber=${fastBookingPhoneNumber}`)
    },

    createFastBooking(fastBookingRequest){
        return url.post("createFastBooking", fastBookingRequest)
    },

    changeStatus(fastBookingId, fastBookingStatus){
        return url.put(`changeStatusFastBooking?fastBookingId=${fastBookingId}&status=${fastBookingStatus}`)
    }
}