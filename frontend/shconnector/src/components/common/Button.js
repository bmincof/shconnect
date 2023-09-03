import React from 'react';
import { Text, StyleSheet, Pressable } from 'react-native';

export default function Button(props) {
  const { onPress, title, backgroundColor, color } = props;
  
  const dynamicStyles = {
    button: {
      backgroundColor: backgroundColor,
    },
    text: {
      color: color,
    }
  }
  
  return (
    <Pressable style={[styles.button, dynamicStyles.button]} onPress={onPress} >
      <Text style={[styles.text, dynamicStyles.text]}>{title}</Text>
    </Pressable>
  );
}

const styles = StyleSheet.create({
  button: {
    alignItems: 'center',
    justifyContent: 'center',
    paddingVertical: 12,
    paddingHorizontal: 32,
    borderRadius: 7,
    elevation: 3,
    marginHorizontal: 12,
    marginBottom: 20,
  },
  text: {
    fontSize: 16,
    lineHeight: 21,
    fontWeight: 'bold',
    letterSpacing: 0.25,
    color: 'white',
  },
});
