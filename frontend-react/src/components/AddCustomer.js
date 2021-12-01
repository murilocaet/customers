import React, { useState } from "react";
import CustumerService from "../services/CustumerService";

const AddCustomer = () => {
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
  const [customer, setCustomer] = useState(initialCustomerState);
  const [msgErro, setMsgErro] = useState([]);
  const [firstNameErro, setFirstNameErro] = useState(false);
  const [lastNameErro, setLastNameErro] = useState(false);
  const [emailErro, setEmailErro] = useState(false);
  const [ageErro, setAgeErro] = useState(false);
  const [stateErro, setStateErro] = useState(false);
  const [cityErro, setCityErro] = useState(false);
  const [submitted, setSubmitted] = useState(false);

  const handleInputChange = event => {
    const { name, value } = event.target;
    setCustomer({ ...customer, [name]: value });

    if(name == "firstName"){setFirstNameErro(false);}
    if(name == "lastName"){setLastNameErro(false);}
    if(name == "email"){setEmailErro(false);}
    if(name == "age"){setAgeErro(false);}
    if(name == "state"){setStateErro(false);}
    if(name == "city"){setCityErro(false);}
  };

  const getData = () => {
    let valid = true;
    if(!customer.firstName){setFirstNameErro(true); valid = false;}
    if(!customer.lastName){setLastNameErro(true); valid = false;}
    if(!customer.email){setEmailErro(true); valid = false;}
    if(!customer.age){setAgeErro(true); valid = false;}
    if(!customer.state){setStateErro(true); valid = false;}
    if(!customer.city){setCityErro(true); valid = false;}
    if(valid){
      return {
        customer: {
          databaseId: null,
          idCustomer: null,
          firstName: customer.firstName,
          lastName: customer.lastName,
          email: customer.email,
          age: customer.age,
          state: customer.state,
          city: customer.city,
          createAt: null,
          updateAt: null,
          removed: false,
          removedAt: null
        }
      }
    }
    return null;
  };

  const saveCustomer = () => {
    let request = getData();
    if(request){
      CustumerService.save(getData())
      .then(response => {
        const { customers, error } = response;

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
          if(customers.length > 0){
            setCustomer(customers[0]);
            setSubmitted(true);
          }
          setMsgErro([]);
        }
      })
      .catch(e => {
      });
    }
    
  };

  const newCustomer = () => {
    setCustomer(initialCustomerState);
    setSubmitted(false);
  };

  return (
    <div className="container">
      <div className="submit-form">
        {submitted ? (
          <div>
            <h4>You submitted successfully!</h4>
            <button className="btn btn-success" onClick={newCustomer}>
              Add
            </button>
          </div>
        ) : (
          <>
            <h4>Customer</h4>
            <div className="form__half-left">
              <div className="form-group">
                <label htmlFor="firstName">First Name</label>
                {firstNameErro ? <span className="span">Required Field!</span> : "" }
                <input
                  type="text"
                  className={"form-control" + (firstNameErro ? " error" : "") }
                  id="firstName"
                  name="firstName"
                  value={customer.firstName}
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
                  value={customer.email}
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
                  value={customer.state}
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
                  value={customer.lastName}
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
                  value={customer.age}
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
                  value={customer.city}
                  onChange={handleInputChange}
                />
              </div>
            </div>

            <div className="col-md-12 Submit">
              <button onClick={saveCustomer} className="btn btn-success">
                Submit
              </button>
            </div>
            
            <div className="col-md-12 msg">
              {msgErro.length > 0 ? "Erros:" : ""}
              {msgErro}
            </div>
          </>
       )}
      </div>
    </div>
  );
};

export default AddCustomer;
