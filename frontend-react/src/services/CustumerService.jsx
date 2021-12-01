import {environment} from '../environment';
import axios from 'axios';

const urlAPI = environment.getApi('customers');

const getAll = (request) => 
  axios.post(urlAPI + "/all" , JSON.stringify(request), environment.HEADER)
  .then(({data}) => data)
  .catch(e => {});

const getById = (id) => 
  axios.get(urlAPI, {
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    },
    params: { 
      id: id
    }
  })
  .then(({data}) => data)
  .catch(e => {});

const save = (request) => 
  axios.post(urlAPI + "/save" , JSON.stringify(request), environment.HEADER)
  .then(({data}) => data)
  .catch(e => {});

const remove = (id) => 
  axios.delete(urlAPI, 
    { 
      data: {}, 
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      params: { 
        id: id
      }
  })
  .then(({data}) => data)
  .catch(e => {});

const disableAll = () => 
  axios.get(urlAPI + "/disableAll", environment.HEADER)
  .then(({data}) => data)
  .catch(e => {});

const activateAll = () => 
  axios.get(urlAPI + "/activateAll", environment.HEADER)
  .then(({data}) => data)
  .catch(e => {});


const CustumerService = {
  getAll,
  getById,
  save,
  remove,
  disableAll,
  activateAll
};

export default CustumerService;