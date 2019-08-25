"use strict";

const React = require('react');
const ReactDOM = require('react-dom');
const ShortID = require("shortid")

class Burger extends React.Component {
    constructor(props) {
        super(props);
        // this.onClickAdd.bind(this);
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
                <button onClick={ () => this.onClickAdd() }>Adicionar</button>
            </div>
        );
    }
}

class OrderItem extends React.Component {
    constructor(props) {
        super(props);
    }

    renderIngredients() {
        const entries = Object.entries(this.props.burger.ingredients);
        return entries.map(ing =>
            <li>{ing[1]}: {ing[0]}</li>
        );
    }

    render() {
        return (
            <div>
                <div className="burger-name">{this.props.burger.name}</div>
                <ul>
                    {  this.renderIngredients() }
                </ul>
                <button onClick={ () => this.props.remove() }>Remover</button>
            </div>
        );
    }
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
        this.requestBurgerList()
    }

    requestBurgerList() {
        fetch("/burgers")
            .then(
                result => result.json()
            )
            .then( resultJson => {
                this.setState({
                    burgers: resultJson,
                    isLoaded: true,
                    orderItems: {}
                });
            })
            .catch(error => this.setState({error}));
    }

    addOrderItem(burger) {
        const prevOrderItems = this.state.orderItems;
        const orderItems = {...prevOrderItems};
        orderItems[ShortID.generate()] = burger;
        this.setState(prevState => ({
            ...prevState,
            orderItems:  orderItems
        }))
    }

    removeOrderItem(key) {
        const prevOrderItems = this.state.orderItems;
        const orderItems = {...prevOrderItems};
        delete  orderItems[key];
        this.setState(prevState => ({
            ...prevState,
            orderItems:  orderItems
        }))
    }

    render() {
        const {error, isLoaded, burgers, orderItems} = this.state;
        if (error) {
            return <div>error.message</div>;
        } else if (!isLoaded) {
            return <div>Carregando...</div>
        } else {
            const burgerComponents = burgers.map(burger =>
                <Burger key={burger.name} burger={burger} add={(burger) => this.addOrderItem(burger)}/>
            );
            if (Object.keys(orderItems).length !== 0) {
                burgerComponents.push(
                    <div>
                        <h2>Pedido</h2>
                        { Object.entries(orderItems).map(entry =>
                            <OrderItem key={entry[0]} burger={entry[1]}
                            remove={() => this.removeOrderItem(entry[0])}/>) }
                    </div>
                )
            }
            return burgerComponents
        }
    }

}

ReactDOM.render(
  <App/>, document.getElementById('react')
);