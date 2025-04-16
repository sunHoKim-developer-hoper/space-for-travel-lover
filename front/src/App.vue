<script setup>
import { watch, ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { apiFetch } from "./utils/api";
import { useUserDetailsStore } from "@/stores/useUserDetailsStore.js";

const userDetails = useUserDetailsStore();
const route = useRoute();

const showHeader = ref(true);

// watch 함수를 사용하지 않으면,, route.name은 계속 undefined만 출력된다.
watch(() => route.name,
  (name) => {
    console.log(name);
    if (name === 'Login')
      showHeader.value = false;
    else
      showHeader.value = true;
  }
);



onMounted(async () => {
  const token = localStorage.getItem("accessToken");
  if (token) {
    try {
      await apiFetch('/api/ping',{
        method : "GET"
      });
    } catch (error) {
      userDetails.logout();
    }
  }
});


</script>

<template>
  <Header v-if="showHeader" />
  <router-view></router-view>
</template>
<style></style>