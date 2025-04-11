import { createApp } from "vue";
//import "./style.css";
import App from "./App.vue";
import pinia from "@/stores/getPinia.js";
import router from "@/router/router.js"
import * as commons from "@/components/common/commonConponent.js"

const app = createApp(App);

// 공통 컴포넌트 등록, 각 페이지에서 import 하지 않아도 사용이 가능
for ( const [key, component] of Object.entries(commons)){
  app.component(key, component);
}

app.use(router).use(pinia).mount("#app");
