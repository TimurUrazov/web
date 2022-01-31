<template>
    <div id="app">
        <Header :user="user"/>
        <Middle :posts="posts" :users="users"/>
        <Footer/>
    </div>
</template>

<script>
import Header from "./components/Header";
import Middle from "./components/Middle";
import Footer from "./components/Footer";
import axios from "axios"

export default {
    name: 'App',
    components: {
        Footer,
        Middle,
        Header
    },
    data: function () {
        return {
            user: null,
            posts: [],
            users: []
        }
    },
    beforeMount() {
        if (localStorage.getItem("jwt") && !this.user) {
            this.$root.$emit("onJwt", localStorage.getItem("jwt"));
        }

        axios.get("/api/1/posts").then(response => {
            this.posts = response.data;
        });

        axios.get("/api/1/users").then(response => this.users = response.data);
    },
    beforeCreate() {
      this.$root.$on("onRegister", (login, password, name) => {
        if (!login) {
          this.$root.$emit("onRegisterValidationError", "Login is required");
        } else if (!name) {
          this.$root.$emit("onRegisterValidationError", "Name is required");
        } else if (name.length > 32) {
          this.$root.$emit("onRegisterValidationError", "Name is too long");
        } else if (login.length < 3) {
          this.$root.$emit("onRegisterValidationError", "Login is too short");
        } else if (login.length > 16) {
          this.$root.$emit("onRegisterValidationError", "Login is too long");
        } else if (!/^[a-z]+$/.test(login)) {
          this.$root.$emit("onRegisterValidationError", "Only lowercase latin letters are allowed");
        } else if (Object.values(this.users).filter(u => u.login === login).length > 0) {
          this.$root.$emit("onRegisterValidationError", "Login is already in use");
        } else {
          axios.post("/api/1/users", {
            login: login,
            password: password,
            name: name
          }).then(response => {
            axios.get("/api/1/users").then(response => response.data);
            this.$root.$emit("onJwt", response.data, true);
          }).catch(error => {
            this.$root.$emit("onRegisterValidationError", error.response.data);
          });
        }
      });

        this.$root.$on("onEnter", (login, password) => {
            if (password === "") {
                this.$root.$emit("onEnterValidationError", "Password is required");
                return;
            }

            axios.post("/api/1/jwt", {
                    login, password
            }).then(response => {
                localStorage.setItem("jwt", response.data);
                this.$root.$emit("onJwt", response.data);
            }).catch(error => {
                this.$root.$emit("onEnterValidationError", error.response.data);
            });
        });

        this.$root.$on("onJwt", (jwt) => {
            localStorage.setItem("jwt", jwt);

            axios.get("/api/1/users/auth", {
                params: {
                    jwt
                }
            }).then(response => {
                this.user = response.data;
                this.$root.$emit("onChangePage", "Index");
            }).catch(() => this.$root.$emit("onLogout"))
        });

        this.$root.$on("onLogout", () => {
            localStorage.removeItem("jwt");
            this.user = null;
        });

    }
}
</script>

<style>
#app {

}
</style>
