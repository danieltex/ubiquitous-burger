"use strict";

const React = require('react');
const ReactDOM = require('react-dom');

class Burger extends React.Component {
    constructor(props) {
        super(props);
    }

    renderIngredients() {
        const entries = Object.entries(this.props.burger.ingredients);
        return entries.map(ing =>
            <li>{ing[1]}: {ing[0]}</li>
        );
    }

    onClickAdd() {
        this.props.add(this.props.burger);
    }

    render() {
        return (
            <div>
                <div className="burger-name">{this.props.burger.name}</div>
                <ul>
                    {  this.renderIngredients() }
                </ul>
                <button onClick={ this.onClickAdd }>Adicionar</button>
            </div>
        );
    }
}

class OrderItem extends React.Component {

}

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            burgers : {},
            isLoaded: false,
            orderItems: {}
        };
    }

    componentDidMount() {
        this.makeRequest()
    }

    makeRequest() {
        fetch("/burgers")
            .then(
                result => result.json()
            )
            .then( resultJson => {
                this.setState({
                    burgers: resultJson,
                    isLoaded: true
                });
            })
            .catch(error => this.setState({error}));
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