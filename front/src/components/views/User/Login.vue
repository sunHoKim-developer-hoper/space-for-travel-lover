<script setup>
import { onMounted, reactive, ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import { apiFetch } from "../../../utils/api";
import { useUserDetailsStore } from "@/stores/useUserDetailsStore.js";

const id = ref('');
const password = ref('');
const router = useRouter();
const userDetails = useUserDetailsStore();

//로그인 되어 있는 상태에서 이 화면 호출 시 튕겨내기
onMounted(() =>{
  if (userDetails.isAuthenticated){
    router.push("/index");
  }
});

async function login() {
  try {
    const response = await apiFetch('/public/login', {
      method: 'POST',
      body: JSON.stringify({ id: id.value, password: password.value })
    });

    saveStores(response);
    router.push("/index");
  } catch (error) {
    alert("로그인 실패!!");
  }
}

const saveStores = (response) => {
  userDetails.id = response.id;
  userDetails.role = response.role;
  localStorage.setItem('accessToken', response.token);
};


</script>
<template>
  <section>
    <div>
      <input v-model="id" type="input">
    </div>
    <div>
      <input v-model="password" type="password" @keyup.enter="login">
    </div>

    <button @click="login">로그인</button>
  </section>
  <!-- <Modal :show="showModal" @ok="dlgOkHandler" type=1 title="입력값을 확인하세요" /> -->
</template>
<style scoped></style>