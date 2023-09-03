import { StatusBar } from "expo-status-bar";
import { StyleSheet, Text, View, Image  } from "react-native";
import { Provider } from "react-redux";
import { Fontisto } from '@expo/vector-icons';
import MyButton from './src/components/common/Button';
import char1 from './assets/character1.png';

import store from "./store";

export default function App() {
  return (
    <Provider store={store}>
      <View style={styles.container}>
        <StatusBar style="auto" />
        <View style={styles.titleCon}>
          <Text style={styles.title}>홈</Text>
          <Fontisto name="bell" size={24} color="black" />
        </View>
        <View style={styles.loginCon}>
          <View style={styles.loginUpper}>
            <View style={styles.loginLeft}>
              <View style={styles.sub}>
                <Text style={styles.grayText}>안녕하세요.</Text>
                <Text style={styles.grayText}>신한 쏠(SOL) 입니다.</Text>
              </View>
              <Text style={styles.boldText}>다양한 서비스 이용을</Text>
              <Text style={styles.boldText}>위해 로그인 해주세요.</Text>
            </View>
            <View style={styles.loginRight}>
              <Image source={char1} resizeMode="contain" style={styles.loginImg} />
            </View>
          </View>
          <MyButton title="로그인" backgroundColor="#2B70CC" color="white" />
        </View>
        <View style={styles.bottom}></View>
      </View>
    </Provider>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#F1F6FD",
    justifyContent: "center",
  },
  titleCon: {
    flex: 0.2,
    flexDirection: "row",
    justifyContent: "space-between",
    marginTop: 45,
    marginHorizontal: 35,
  },
  title: {
    fontSize: 24,
    fontWeight: "600",
  },
  loginCon: {
    flex: 1.3,
    margin: 30,
    paddingHorizontal: 20,
    borderRadius: 15,
    backgroundColor: "white",
  },
  loginUpper: {
    flex: 2,
    flexDirection: "row",
    paddingLeft: 12,
  },
  loginImg: {
    width:"80%",
    height:"80%",
  },
  loginLeft: {
    flex: 1.5,
    justifyContent: "center"
  },
  loginRight: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  bottom: {
    flex: 3,
    backgroundColor: "green",
  },
  grayText: {
    fontSize: 16,
    color: "gray",
  },
  boldText: {
    fontSize: 18,
    fontWeight: "700",
    letterSpacing: 0.2,
  },
  sub: {
    marginBottom: 12,
  }
});
