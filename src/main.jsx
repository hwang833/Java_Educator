import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'

import { GoogleOAuthProvider } from '@react-oauth/google'
//TODO: cyber security this
ReactDOM.createRoot(document.getElementById('root')).render(
  <GoogleOAuthProvider clientId="1086634105215-ph1ap394e23f6on329ovm9otc5306k5s.apps.googleusercontent.com">
    <App />
  </GoogleOAuthProvider>
)
