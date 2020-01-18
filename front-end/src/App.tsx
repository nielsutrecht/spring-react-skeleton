import React from 'react';
import logo from './logo.svg';
import './App.css';

const App: React.FC = () => {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
          <Ip></Ip>
      </header>
    </div>
  );
}

class Ip extends React.Component {
    url = process.env.NODE_ENV === 'development' ? 'http://localhost:8080/api/ip' : '/api/ip'
    state = {
        ip: ""
    }
    componentDidMount() {
        fetch(this.url)
            .then(res => res.json())
            .then((data) => {
                this.setState({ ip: data.ip })
            })
            .catch(console.log)
    }
    render() {
        return (
            <div>
                <p>My IP is: {this.state.ip}</p>
            </div>
        );
    }
}

export default App;
