import axios from "axios";
import { baseUrl } from "../config";

export const createContact = (contact) => {
  return axios.post(`${baseUrl}/contacts`, contact);
};

export const updateContact = (contact) => {
  return axios.put(`${baseUrl}/contacts/${contact.id}`, contact);
};

export const deleteContact = (id) => {
  return axios.delete(`${baseUrl}/contacts/${id}`);
};

export const getContact = (id) => {
  return axios.get(`${baseUrl}/contacts/${id}`);
};

export const getContacts = (searchText) => {
  if (!searchText) {
    searchText = "";
  }
  return axios.get(`${baseUrl}/contacts?search=${searchText}`);
};
