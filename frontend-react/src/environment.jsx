
//const HOST = "http://192.168.1.7:";
const HOST = "http://ec2-54-205-233-46.compute-1.amazonaws.com:";
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