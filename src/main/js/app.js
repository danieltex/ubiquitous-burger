"use strict";

const React = require('react');
const ReactDOM = require('react-dom');

class Burger extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>{this.props.burger.name}</div>
        );
    }
}

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            burgers : {},
            isLoaded: false,
        };
    }

    componentDidMount() {
        this.makeRequest()
    }

    makeRequest() {
        fetch("/burgers")
            .then(result => {
                return result.json();
            })
            .then( resultJson => {
                this.setState({
                    burgers: resultJson,
                    isLoaded: true
                })
            }).catch(error => {
                this.setState({error})
            });
    }

    render() {
        const {error, isLoaded, burgers} = this.state;
        if (error) {
            return <div>error.message</div>;
        } else if (!isLoaded) {
            return <div>Carregando...</div>
        } else {
            return burgers.map(burger =>
                <Burger key={burger.name} burger={burger} />
            );
        }
    }

}

ReactDOM.render(
  <App/>, document.getElementById('react')
);