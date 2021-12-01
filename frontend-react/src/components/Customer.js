import React, { useState, useEffect } from "react";
import CustomerDataService from "../services/CustumerService";

const Customer = props => {
  const initialCustomerState = {
    databaseId: null,
    idCustomer: "",
    firstName: "",
    lastName: "",
    email: "",
    age: "",
    state: "",
    city: "",
    createAt: "",
    updateAt: "",
    removed: "",
    removedAt: null
  };
  const [currentCustomer, setCurrentCustomer] = useState(initialCustomerState);
  const [message, setMessage] = useState("");
  const [msgErro, setMsgErro] = useState([]);
  const [firstNameErro, setFirstNameErro] = useState(false);
  const [lastNameErro, setLastNameErro] = useState(false);
  const [emailErro, setEmailErro] = useState(false);
  const [ageErro, setAgeErro] = useState(false);
  const [stateErro, setStateErro] = useState(false);
  const [cityErro, setCityErro] = useState(false);
  const [submitted, setSubmitted] = useState(false);

  const getCustomer = id => {
    CustomerDataService.getById(id)
      .then(response => {
        if(response.customers.length > 0){
          setCurrentCustomer(response.customers[0]);
        }
      })
      .catch(e => {
      });
  };

  useEffect(() => {
    getCustomer(props.match.params.id);
  }, [props.match.params.id]);

  const handleInputChange = event => {
    const { name, value } = event.target;
    setCurrentCustomer({ ...currentCustomer, [name]: value });

    if(name == "firstName"){setFirstNameErro(false);}
    if(name == "lastName"){setLastNameErro(false);}
    if(name == "email"){setEmailErro(false);}
    if(name == "age"){setAgeErro(false);}
    if(name == "state"){setStateErro(false);}
    if(name == "city"){setCityErro(false);}
  };

  const getData = status => {
    return {
      customer: {
        databaseId: currentCustomer.databaseId,
        idCustomer: currentCustomer.idCustomer,
        firstName: currentCustomer.firstName,
        lastName: currentCustomer.lastName,
        email: currentCustomer.email,
        age: currentCustomer.age,
        state: currentCustomer.state,
        city: currentCustomer.city,
        createAt: currentCustomer.createAt,
        updateAt: currentCustomer.updateAt,
        removed: status,
        removedAt: null
      }
    }
  };

  const getDataUpdate = status => {
    let valid = true;
    if(!currentCustomer.firstName){setFirstNameErro(true); valid = false;}
    if(!currentCustomer.lastName){setLastNameErro(true); valid = false;}
    if(!currentCustomer.email){setEmailErro(true); valid = false;}
    if(!currentCustomer.age){setAgeErro(true); valid = false;}
    if(!currentCustomer.state){setStateErro(true); valid = false;}
    if(!currentCustomer.city){setCityErro(true); valid = false;}
    if(valid){
      return {
        customer: {
          databaseId: currentCustomer.databaseId,
          idCustomer: currentCustomer.idCustomer,
          firstName: currentCustomer.firstName,
          lastName: currentCustomer.lastName,
          email: currentCustomer.email,
          age: currentCustomer.age,
          state: currentCustomer.state,
          city: currentCustomer.city,
          createAt: currentCustomer.createAt,
          updateAt: currentCustomer.updateAt,
          removed: status,
          removedAt: null
        }
      }
    }
    return null;
  };

  const activateCustomer = () => {
    CustomerDataService.save(getData(false))
      .then(response => {
        
        if(response.customers.length > 0){
          setCurrentCustomer(response.customers[0]);
          setMessage("The status was updated successfully!");
        }
      })
      .catch(e => {
      });
  };

  const updateCustomer = () => {
    let request = getDataUpdate(currentCustomer.removed);
    if(request){
      CustomerDataService.save(request)
      .then(response => {
        const { error } = response;

        if(error.length > 0){
          let msg = [];
          for(let i = 0; i < error.length; i++){
            msg.push(
              <div key={"msg-"+i} className="col-md-12 item">
                {(i+1) + ". " + error[i]}
              </div>
            );
          }
          setMsgErro(msg);
        }else{
          setMessage("The customer was updated successfully!");
          setMsgErro([]);
        }
      })
      .catch(e => {
      });
    }
  };

  const disableCustomer = () => {
    CustomerDataService.remove(currentCustomer.idCustomer)
      .then(response => {
        
        if(response.customers.length > 0){
          setCurrentCustomer(response.customers[0]);
          setMessage("The status was updated successfully!");
        }
      })
      .catch(e => {
      });
  };

  return (
    <div className="container">
      {currentCustomer && currentCustomer.databaseId ? (
        <div className="edit-form">
          <h4>Customer</h4>
          <form>
            <div className="form__half-left">
              <div className="form-group">
                <label htmlFor="firstName">First Name</label>
                {firstNameErro ? <span className="span">Required Field!</span> : "" }
                <input
                  type="text"
                  className={"form-control" + (firstNameErro ? " error" : "") }
                  id="firstName"
                  name="firstName"
                  value={currentCustomer.firstName}
                  onChange={handleInputChange}
                />
              </div>
              <div className="form-group">
                <label htmlFor="email">E-mail</label>
                {emailErro ? <span className="span">Required Field!</span> : "" }
                <input
                  type="text"
                  className={"form-control" + (emailErro ? " error" : "") }
                  id="email"
                  name="email"
                  value={currentCustomer.email}
                  onChange={handleInputChange}
                />
              </div>
              <div className="form-group">
                <label htmlFor="state">State</label>
                {stateErro ? <span className="span">Required Field!</span> : "" }
                <input
                  type="text"
                  className={"form-control" + (stateErro ? " error" : "") }
                  id="state"
                  name="state"
                  value={currentCustomer.state}
                  onChange={handleInputChange}
                />
              </div>
            </div>
            <div className="form__half-right">
              <div className="form-group">
                <label htmlFor="lastName">Last Name</label>
                {lastNameErro ? <span className="span">Required Field!</span> : "" }
                <input
                  type="text"
                  className={"form-control" + (lastNameErro ? " error" : "") }
                  id="lastName"
                  name="lastName"
                  value={currentCustomer.lastName}
                  onChange={handleInputChange}
                />
              </div>
              <div className="form-group">
                <label htmlFor="age">Age</label>
                {ageErro ? <span className="span">Required Field!</span> : "" }
                <input
                  type="text"
                  className={"form-control" + (ageErro ? " error" : "") }
                  id="age"
                  name="age"
                  value={currentCustomer.age}
                  onChange={handleInputChange}
                />
              </div>
              <div className="form-group">
                <label htmlFor="city">City</label>
                {cityErro ? <span className="span">Required Field!</span> : "" }
                <input
                  type="text"
                  className={"form-control" + (cityErro ? " error" : "") }
                  id="city"
                  name="city"
                  value={currentCustomer.city}
                  onChange={handleInputChange}
                />
              </div>
            </div>
            <div className="form-group">
              <label>
                <strong>Status:</strong>
              </label>
              {currentCustomer.removed ? " Inactive" : " Active"}
            </div>
          </form>

          {currentCustomer.removed ? (
            <button
              className="badge badge-primary mr-2"
              onClick={() => activateCustomer()}
            >
              Activate
            </button>
          ) : (
            <button
              className="badge badge-danger mr-2"
              onClick={() => disableCustomer()}
            >
              Disable
            </button>
          )}

          <button
            type="submit"
            className="badge badge-success"
            onClick={updateCustomer}
          >
            Update
          </button>
          <p>{message}</p>
            
          <div className="col-md-12 msg">
            {msgErro.length > 0 ? "Erros:" : ""}
            {msgErro}
          </div>
        </div>
      ) : (
        <div>
          <br />
          <p>Customer not found...</p>
        </div>
      )}
    </div>
  );
};

export default Customer;
