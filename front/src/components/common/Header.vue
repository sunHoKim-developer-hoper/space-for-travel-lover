<script setup>
import { useUserDetailsStore } from "@/stores/useUserDetailsStore.js";
const userDetails = useUserDetailsStore();

console.log("Header mounted");
console.log("ID:", userDetails.id);
console.log("ROLE:", userDetails.role);
</script>

<template>
    <h2>I am Header</h2>
</template>

<style scoped></style>
