package com.meryem.mlogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataModal {


    @SerializedName("name")
    @Expose
        private String name;
    @SerializedName("email")
    @Expose
        private String email;
    @SerializedName("username")
    @Expose
        private String username;
    @SerializedName("password")
    @Expose
        private String password;
    @SerializedName("passwordVerify")
    @Expose
         private String passwordVerify;

        public DataModal(String name, String email, String username, String password, String passwordVerify) {
            this.name = name;
            this.email = email;
            this.username = username;
            this.password = password;
            this.passwordVerify = passwordVerify;

        }

    public DataModal(String username,String password) {
        this.username = username;
        this.password = password;

    }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String job) {
            this.email = email;
        }

        public String getUsername() {
        return username;
        }

        public void setUsername(String username) {

            this.username = username;
       }
        public String getPassword() {
        return password;
    }

       public void setPassword(String password) {
        this.password = password;
    }

       public String getPasswordVerify() {
        return password;
    }

       public void setPasswordVerify(String passwordVerify) {
        this.passwordVerify = passwordVerify;
      }
}

