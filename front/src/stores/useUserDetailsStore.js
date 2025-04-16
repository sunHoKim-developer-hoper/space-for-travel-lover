import { defineStore } from "pinia";

export const useUserDetailsStore = defineStore("userDetails", {
  state: () => ({
    id: null,
    role : null
  }),

  getters: {
    isAuthenticated: (state) => state.id == null ? false : true
  },

  actions: {
    logout() {
      this.id = null;
      this.role = null
      localStorage.removeItem('accessToken');
      //this.profileSrc = null;
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