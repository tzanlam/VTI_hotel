import { url } from "./axios"

export const VoucherService = {
    fetchVouchers(){
        return url.get("findVouchers")
    },

    fetchVoucherById(voucherId){
        return url.get(`findVoucherById?voucherId=${voucherId}`)
    },

    fetchVoucherByPoint(voucherPoint){
        return url.get(`findVoucherByPoint?voucherPoint=${voucherPoint}`)
    },

    fetchVoucherActive(){
        return url.get(`findVoucherActive`)
    },

    createVoucher(voucherRequest){
        return url.post("createVoucher", voucherRequest)
    },

    updateVoucher(voucherId, voucherRequest){
        return url.put(`updateVoucher?voucherId=${voucherId}`, voucherRequest)
    },

    changeVoucherStatus(voucherId){
        return url.get("changeStatusVoucher?voucherId="+voucherId)
    }
}