import { NativeModules } from "react-native";

const { NativeModuleExample } = NativeModules;

export const sum = (a: number, b: number): Promise<number> => {
    return NativeModuleExample.sum(a, b)
}

export const openCalendar = (eventTitle: string, eventLocation: string): Promise<string> => {
    return NativeModuleExample.openCalendar(eventTitle, eventLocation)
}