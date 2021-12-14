import {environment} from '../environment';
import axios from 'axios';

const urlAPI = environment.getApi('customers');

const getAll = (request) => 
  axios.get(urlAPI, 
    { 
      data: {}, 
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      params: { 
        page: request.page,
        count: request.count,
        pageSize: request.pageSize,
        totalItens: request.totalItens,
        searchName: request.searchName
      }
  })
  .then(({data}) => data)
  .catch(e => {});


const getById = (id) => 
  axios.get(urlAPI + "/" + id , environment.HEADER)
  .then(({data}) => data)
  .catch(e => {});

const save = (request) => 
  axios.post(urlAPI, JSON.stringify(request), environment.HEADER)
  .then(({data}) => data)
  .catch(e => {});

const edit = (request) => 
  axios.put(urlAPI + "/" + request.customer.idCustomer, JSON.stringify(request), environment.HEADER)
  .then(({data}) => data)
  .catch(e => {});

const remove = (id) => 
 axios.delete(urlAPI + "/" + id , environment.HEADER)
  .then(({data}) => data)
  .catch(e => {});

const activate = (id) => 
  axios.patch(urlAPI + "/activate/" + id,  null, environment.HEADER)
  .then(({data}) => data)
  .catch(e => {});

const activateAll = () => 
  axios.patch(urlAPI + "/activateAll", null, environment.HEADER)
  .then(({data}) => data)
  .catch(e => {});

const disable = (id) => 
  axios.patch(urlAPI + "/disable/" + id, null, environment.HEADER)
  .then(({data}) => data)
  .catch(e => {});

const disableAll = () => 
  axios.patch(urlAPI + "/disableAll", null, environment.HEADER)
  .then(({data}) => data)
  .catch(e => {});

const CustumerService = {
  getAll,
  getById,
  save,
  edit,
  remove,
  activate,
  activateAll,
  disable,
  disableAll
};

export default CustumerService;