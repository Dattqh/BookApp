import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, Image, TouchableOpacity, FlatList, ActivityIndicator } from 'react-native';
import Icon from 'react-native-vector-icons/Ionicons';

export default function HomeScreen({ navigation }) {
  const [hotStories, setHotStories] = useState([]);
  const [updatedStories, setUpdatedStories] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchBooks = async () => {
      try {
        const hotRes = await fetch('http://YOUR_SERVER_IP:3000/books/hot');
        const hotData = await hotRes.json();
        setHotStories(hotData);

        const updatedRes = await fetch('http://YOUR_SERVER_IP:3000/books/updated');
        const updatedData = await updatedRes.json();
        setUpdatedStories(updatedData);
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    fetchBooks();
  }, []);

  if (loading) {
    return (
      <View style={styles.centered}>
        <ActivityIndicator size="large" color="#6B8E23" />
      </View>
    );
  }

  return (
    <View style={styles.container}>
      {/* Header */}
      <View style={styles.header}>
        <Text style={styles.headerText}>Home</Text>
        <Icon name="code-slash" size={28} color="#6B8E23" />
      </View>

      {/* User Info Row */}
      <View style={styles.userRow}>
        <Image source={{ uri: 'https://i.pravatar.cc/100' }} style={styles.avatar} />
        <TouchableOpacity style={styles.loginButton} onPress={() => navigation.navigate('Login')}>
          <Text style={styles.loginText}>Hi, StrBerryAw!</Text>
        </TouchableOpacity>
        <Icon name="search" size={24} style={styles.icon} />
        <Icon name="menu" size={24} style={styles.icon} />
      </View>

      {/* Action Buttons */}
      <View style={styles.actionRow}>
        <View style={styles.actionItem}>
          <View style={styles.actionCircle}>
            <Icon name="star" size={28} color="#6B8E23" />
          </View>
          <Text style={styles.actionLabel}>Rate</Text>
        </View>
        <View style={styles.actionItem}>
          <View style={styles.actionCircle}>
            <Icon name="heart" size={28} color="#6B8E23" />
          </View>
          <Text style={styles.actionLabel}>Like</Text>
        </View>
        <View style={styles.actionItem}>
          <View style={styles.actionCircle}>
            <Icon name="eye" size={28} color="#6B8E23" />
          </View>
          <Text style={styles.actionLabel}>View</Text>
        </View>
      </View>

      {/* Hot Stories */}
      <View style={styles.sectionHeader}>
        <Text style={styles.sectionTitle}>Hot Stories</Text>
        <Icon name="flame" size={20} color="black" />
      </View>
      <FlatList
        data={hotStories}
        horizontal
        showsHorizontalScrollIndicator={false}
        keyExtractor={item => item._id}
        style={{ marginBottom: 10 }}
        renderItem={({ item }) => (
          <View style={styles.bookItem}>
            <Image source={{ uri: item.image }} style={styles.bookImage} />
            <Text style={styles.bookTitle} numberOfLines={2}>{item.title}</Text>
            <Text style={styles.bookAuthor}>{item.author}</Text>
          </View>
        )}
      />

      {/* Newly Updated Story */}
      <Text style={styles.sectionTitle}>NEWLY UPDATED STORY</Text>
      <FlatList
        data={updatedStories}
        horizontal
        showsHorizontalScrollIndicator={false}
        keyExtractor={item => item._id}
        renderItem={({ item }) => (
          <View style={styles.bookItem}>
            <Image source={{ uri: item.image }} style={styles.bookImage} />
            <Text style={styles.bookTitle} numberOfLines={2}>{item.title}</Text>
            <Text style={styles.bookAuthor}>{item.author}</Text>
          </View>
        )}
      />

      {/* Floating Book Button */}
      <TouchableOpacity style={styles.fab}>
        <Icon name="book" size={32} color="#fff" />
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#F7F7F7', paddingTop: 30 },
  header: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', paddingHorizontal: 16, marginBottom: 8 },
  headerText: { color: '#B0B0B0', fontSize: 18, fontWeight: 'bold' },
  userRow: { flexDirection: 'row', alignItems: 'center', paddingHorizontal: 16, marginBottom: 16 },
  avatar: { width: 40, height: 40, borderRadius: 20, marginRight: 10 },
  loginButton: { backgroundColor: '#B6C49B', borderRadius: 20, paddingHorizontal: 16, paddingVertical: 8 },
  loginText: { color: '#333', fontWeight: 'bold' },
  icon: { marginLeft: 16, color: '#333' },
  actionRow: { flexDirection: 'row', justifyContent: 'space-around', marginVertical: 16 },
  actionItem: { alignItems: 'center' },
  actionCircle: { backgroundColor: '#E5E9DF', borderRadius: 30, width: 60, height: 60, justifyContent: 'center', alignItems: 'center', marginBottom: 4 },
  actionLabel: { fontSize: 14, color: '#333' },
  sectionHeader: { flexDirection: 'row', alignItems: 'center', marginLeft: 16, marginBottom: 4 },
  sectionTitle: { fontWeight: 'bold', fontSize: 16, marginRight: 6, color: '#222', marginLeft: 16, marginTop: 10 },
  bookItem: { width: 110, marginHorizontal: 8 },
  bookImage: { width: 100, height: 130, borderRadius: 8, marginBottom: 6 },
  bookTitle: { fontWeight: 'bold', fontSize: 13, color: '#222' },
  bookAuthor: { fontSize: 11, color: '#888' },
  fab: { position: 'absolute', bottom: 24, alignSelf: 'center', backgroundColor: '#B6C49B', borderRadius: 32, width: 64, height: 64, justifyContent: 'center', alignItems: 'center', elevation: 4 },
  centered: { flex: 1, justifyContent: 'center', alignItems: 'center' },
});
