<script setup>
import { watch, ref } from 'vue';
import { useRoute } from 'vue-router';
const route = useRoute();

let showFlag = ref(true);

// watch 함수를 사용하지 않으면,, route.name은 계속 undefined만 출력된다.
// watch(
//   () => route.name,
//   (name) => {
//     console.log(name);
//     if (name === 'NotFound')
//       showFlag.value = false;
//     else
//       showFlag.value = true;
//   }
// );

</script>

<template>
  <Header />
  <router-view></router-view>
</template>
<style></style>