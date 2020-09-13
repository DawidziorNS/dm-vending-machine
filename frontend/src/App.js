import React, { useState, useEffect } from "react";
import "./App.css";
import { simpleJsonFetch } from "./httpHelper";

function App() {
  const [products, setProducts] = useState();
  const [fetched, setFetched] = useState(false);

  const fetchProducts = () => {
    if (!fetched) {
      simpleJsonFetch("/api/products").then(res => {
        setProducts(res);
        setFetched(true);
      });
    }
  };

  useEffect(fetchProducts);

  return (
    <div className="App">
      <header className="App-header">{products}</header>
    </div>
  );
}

export default App;
