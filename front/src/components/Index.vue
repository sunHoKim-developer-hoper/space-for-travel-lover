<script setup>
import { useUserDetailsStore } from "@/stores/useUserDetailsStore.js";
const userDetails = useUserDetailsStore();
import { useRouter, useRoute } from "vue-router";
const router = useRouter();

function logout (){
    userDetails.logout();
}

function login (){
    router.push("/login");
}


</script>

<template>

    <h2>I am Index Page</h2>
    <button @click="logout" v-if="userDetails.id">logout</button>
    <button @click="login" v-if="!userDetails.id">login</button>
</template>

<style scoped></style>
