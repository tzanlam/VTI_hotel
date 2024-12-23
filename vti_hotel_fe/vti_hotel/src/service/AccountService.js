import { url } from "./axios"

export const AccountService = {
    fetchAccounts(){
        return url.get("findAccounts")
    },
    
    fetchAccountById(accountId){
        return url.get(`findAccountById?accountId=${accountId}`)
    },

    fetchAccountByEmail(email){
        return url.get(`findAccountByEmail?email=${email}`)
    },

    craeteAccount(accountRequest){
        return url.post(`createAccountByAdmin`, accountRequest)
    },

    updateAccount(accountId, accountRequest){
        return url.put(`updateAccount?accountId=${accountId}`, accountRequest)
    },

    register(accountRequest){
        return url.put("register", accountRequest)
    }
}