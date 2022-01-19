/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * Generated with the TypeScript template
 * https://github.com/react-native-community/react-native-template-typescript
 *
 * @format
 */

import React, { useEffect, useState } from 'react';
import {
  Alert,
  Dimensions,
  GestureResponderEvent,
  Image,
  Pressable,
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleProp,
  StyleSheet,
  Text,
  TextInput,
  useColorScheme,
  View,
  ViewStyle,
} from 'react-native';

import {
  Colors,
  DebugInstructions,
  Header,
  LearnMoreLinks,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';
import { openCalendar, sum } from './aula_native_modules';

const App = () => {
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  const [value, setValue] = useState<string>('');
  const [eventName, setEventName] = useState<string>('')
  const [eventLocation, setEventLocation] = useState<string>('')

  // useEffect(() => {
  //   calc(5, 8);
  // }, []);

  // const calc = async (a: number, b: number) => {
  //   const result = await sum(a, b);
  //   setValue(result)
  // }

  const onClickCreateEventHandler = async () => {
    if (eventName && eventLocation) {
      const eventID = await openCalendar(eventName, eventLocation)
      setValue(eventID)
    } else {
      Alert.alert(
        "Alerta",
        "Por favor, preencha todos os campos para criar um novo evento",
      );
    }
  }

  const eventMessage = (value: string) => {
    return (
      <Text>
        Evento <Text style={{ color: '#f45567' }} >
          {value}
        </Text> criado com sucesso!
      </Text>
    );
  }

  const Button = ({ style, title, onPress }: { style: StyleProp<ViewStyle>, title: string, onPress?: (event: GestureResponderEvent) => void }) => {
    return (
      <Pressable style={style} onPress={onPress} android_ripple={{ color: 'pink' }}>
        <Text style={{ textAlign: 'center', color: 'white', fontWeight: 'bold', fontSize: 18 }} >
          {title}
        </Text>
      </Pressable>
    );
  }

  return (
    <SafeAreaView style={{ flex: 1, backgroundColor: '#f0f4f8' }}>
      <ScrollView contentContainerStyle={{ flexGrow: 1 }} >
        <View style={{ alignItems: 'center', justifyContent: 'center', flex: 1, padding: 8 }} >
          <Image
            style={{ height: 150 }}
            resizeMode='contain'
            source={require('./assets/images/party-popper.png')} />
          <Text style={{ color: '#2b2c32', fontSize: 18, fontWeight: 'bold', marginTop: 24, width: 300, textAlign: 'center' }} >
            {value.length <= 0 || eventMessage(value)}
          </Text>
        </View>
        <View style={{ borderRadius: 10, width: 50, height: 5, backgroundColor: '#648da5', alignSelf: 'center', margin: 8 }} />
        <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center', padding: 8 }} >
          <TextInput onChangeText={t => setEventName(t)} placeholder='Nome do evento' placeholderTextColor='rgba(246, 81, 104, 0.6)' style={{ borderBottomWidth: 1, borderBottomColor: '#f65168', color: '#f65168', width: 300, marginBottom: 24 }} />
          <TextInput onChangeText={t => setEventLocation(t)} placeholder='Local' placeholderTextColor='rgba(246, 81, 104, 0.6)' style={{ borderBottomWidth: 1, borderBottomColor: '#f65168', color: '#f65168', width: 300 }} />
        </View>
        <Button style={{ backgroundColor: '#f65168', width: 300, borderRadius: 5, alignSelf: 'center', margin: 18, padding: 18 }} title='Criar Evento' onPress={onClickCreateEventHandler} />
      </ScrollView>
    </SafeAreaView >
  );
};

export default App;
