
//const HOST = "http://192.168.1.7:";
//const HOST = "http://ec2-54-152-102-37.compute-1.amazonaws.com:";
const HOST = "http://localhost:";
const PORT_API = "8081";
const HOST_API = HOST + PORT_API + "/";

const HEADER = {
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
}

export const environment = {
    getApi,
    HEADER
};

function getApi(path) {
    return HOST_API + "api/" + path;
}