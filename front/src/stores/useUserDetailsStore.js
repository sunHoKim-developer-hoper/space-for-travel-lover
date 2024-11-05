import { defineStore } from "pinia";

export const useUserDetailsStore = defineStore("userDetails", {
  state: () => ({
    id: '김선호'
  }),

  getters: {
    isAuthenticated: (state) => state.email == null ? false : true
  },

  actions: {
    logout() {
      this.id = null;
      this.email = null;
      this.nickname = null;
      this.profileSrc = null;
      this.url = null;
    },

    hasRole() {
      let result = this.email == null ? false : true;
      return result;
    }
  },

  persist: {
    enabled: true
  }

});