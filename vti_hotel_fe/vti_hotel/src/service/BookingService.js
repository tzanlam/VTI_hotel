import { url } from "./axios"

export const BookingService = {
    fetchBookings(){
        return url.get("findBookings")
    },

    fetchBookingById(bookingId){
        return url.get(`findBookingById?bookingId=${bookingId}`)
    },

    fetchBookingByUsername(bookingName){
        return url.get(`findBookingByName?username=${bookingName}`)
    },

    createBooking(bookingRequest){
        return url.post("createBooking", bookingRequest)
    },

    updateBooking(bookingId, bookingRequest){
        return url.put(``)
    }
}