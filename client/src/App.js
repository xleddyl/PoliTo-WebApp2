import { useState } from "react";
import { callAPI } from "./api/API";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import ApiTest from "./components/ApiTest.jsx";
import Signup from "./components/Signup";

function App() {
  const [open, setOpen] = useState(false);
  const [method, setMethod] = useState("GET");
  const [body, setBody] = useState("");
  const [route, setRoute] = useState("");
  const [error, setError] = useState("");
  const [response, setResponse] = useState("");
  const httpMethods = ["GET", "POST", "PUT", "DELETE"];

  const changeMethod = (m) => {
    setOpen(false);
    setMethod(m);
    setBody("");
  };

  const makeRequest = async () => {
    setError("");
    setResponse("");
    try {
      const parsedBody = body ? JSON.parse(body) : undefined;
      const response = await callAPI(method, route, parsedBody, "/api");
      setResponse(response);
    } catch (e) {
      setError(e.toString());
    }
  };

  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={
            <>
              <ApiTest
                setOpen={setOpen}
                changeMethod={changeMethod}
                body={body}
                open={open}
                method={method}
                setBody={setBody}
                setRoute={setRoute}
                makeRequest={makeRequest}
                httpMethods={httpMethods}
                response={response}
                error={error}
              />
            </>
          }
        ></Route>
        <Route
          path="/signup"
          element={<Signup />}
        ></Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
