<script setup>
import { reactive, ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import { apiFetch } from "../../../utils/api";

const id = ref('');
const password = ref('');
const name = ref('');
const birthDate = ref('');

const router = useRouter();

async function login(){
  try {
    const response = await apiFetch('/auth/login', {
      method : 'POST',
      body : JSON.stringify({id : id.value, password :password.value})
    })
    localStorage.setItem('accessToken',response.token);
  } catch (error) {
    alert("로그인 실패!!");
  }
}
</script>
<template>
  <section>
    <div>
      <input v-model="id" type="input">
    </div>
    <div>
      <input v-model="password" type="password">
    </div>

    <button @click="login">로그인</button>
  </section>
  <!-- <Modal :show="showModal" @ok="dlgOkHandler" type=1 title="입력값을 확인하세요" /> -->
</template>
<style scoped></style>