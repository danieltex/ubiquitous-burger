"use strict";

const React = require('react');
const ReactDOM = require('react-dom');
const ShortID = require("shortid")

function calcPrice(burger, ingredients) {
    return Object.entries(burger.ingredients)
        .map(entry => {
            const [name, count] = entry;
            return ingredients[name] * count;
        })
        .reduce((a, b) => a + b);
}

function renderIngredients(ingredients) {
    return Object.entries(ingredients)
        .map(entry => {
            const [name, count] = entry;
            if (count > 1) {
                return name + "(" + count + ")";
            } else {
                return name;
            }
        })
        .sort((a, b) => a.localeCompare(b))
        .join(", ");
}

class Burger extends React.Component {
    constructor(props) {
        super(props);
    }

    onClickAdd() {
        this.props.add(this.props.burger);
    }

    render() {
        return (
            <div className="menu-item">
                <div className="burger-name">{this.props.burger.name}</div>
                <div>
                    {calcPrice(this.props.burger, this.props.ingredients)
                        .toLocaleString("pt-BR", { style: 'currency', currency: 'BRL' })}
                </div>
                <div>
                    {  renderIngredients(this.props.burger.ingredients) }
                </div>
                <button onClick={ () => this.onClickAdd() }>Adicionar</button>
            </div>
        );
    }
}

class OrderItem extends React.Component {
    constructor(props) {
        super(props);
        this.state = {openIngredients: false}
    }

    toggleIngredients() {
        this.setState({
            openIngredients: !this.state.openIngredients
        });
    }

    addIngredient(name) {
        const ingredients = {...this.props.burger.ingredients};
        if (!ingredients[name]) {
            ingredients[name] = 1;
        } else {
            ingredients[name] += 1;
        }
        const burger = {
            ...this.props.burger,
            ingredients
        };
        this.props.update(this.props.id, burger);
    }

    removeIngredient(name) {
        const ingredients = {...this.props.burger.ingredients};
        ingredients[name] -= 1;
        if (ingredients[name] === 0) {
            delete ingredients[name];
        }
        const burger = {
            ...this.props.burger,
            ingredients
        };
        this.props.update(this.props.id, burger);
    }

    renderAvailablerIngredients() {
        const entries = Object.entries(this.props.ingredients);
        return (
            <div>
                <button onClick={() => this.toggleIngredients()}>Incrementar [{
                    this.state.openIngredients ? '+' : '-'
                }]</button>
                <div className={"collapse" + (this.state.openIngredients ? " in" : "")}>
                    <table>
                        <tbody>
                        {entries.map(ing =>
                            <tr>
                                <td>{ing[0]}</td>
                                <td>{ing[1].toLocaleString("pt-BR", { style: 'currency', currency: 'BRL' })}</td>
                                <td>
                                    <button onClick={() => this.addIngredient(ing[0])}>+</button>
                                    <button onClick={() => this.removeIngredient(ing[0])}
                                    disabled={!Boolean(this.props.burger.ingredients[ing[0]])}>-</button>
                                </td>
                            </tr>)}
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }

    render() {
        return (
            <div className="order-item">
                <div className="burger-name">{this.props.burger.name}</div>
                <div>{calcPrice(this.props.burger, this.props.ingredients)
                    .toLocaleString("pt-BR", { style: 'currency', currency: 'BRL' })}</div>
                <div>{ renderIngredients(this.props.burger.ingredients) }</div>
                <button onClick={ () => this.props.remove() }>Remover</button>
                {this.renderAvailablerIngredients()}
            </div>
        );
    }
}

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            burgers: [],
            ingredients: {},
            isLoaded: false,
            orderItems: {},
        };
    }

    componentDidMount() {
        this.requestBurgerList();
        this.requestIngredientsList();
    }

    requestBurgerList() {
        fetch("/burgers")
            .then(
                result => result.json()
            )
            .then( resultJson => {
                this.setState(prevState => ({
                    ...prevState,
                    burgers: resultJson,
                    isLoaded: true,
                }));
            })
            .catch(error => this.setState({error}));
    }

    requestIngredientsList() {
        fetch("/ingredients")
            .then(
                result => result.json()
            )
            .then( resultJson => {
                this.setState(prevState => ({
                    ...prevState,
                    ingredients: resultJson,
                    isLoaded: true,
                }));
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

    updateOrder(key, burger) {
        const prevOrderItems = this.state.orderItems;
        const orderItems = {...prevOrderItems};
        orderItems[key] = burger;
        this.setState(prevState => ({
            ...prevState,
            orderItems:  orderItems
        }))
    }

    removeOrderItem(key) {
        const prevOrderItems = this.state.orderItems;
        const orderItems = {...prevOrderItems};
        delete orderItems[key];
        this.setState(prevState => ({
            ...prevState,
            orderItems:  orderItems
        }))
    }

    submitOrder() {

    }

    render() {
        const {error, isLoaded, burgers, orderItems} = this.state;
        if (error) {
            return <div>error.message</div>;
        } else if (!isLoaded) {
            return <div>Carregando...</div>
        } else {
            const burgerComponents = burgers.map(burger =>
                <Burger
                    key={burger.name}
                    burger={burger}
                    ingredients={this.state.ingredients}
                    add={(burger) => this.addOrderItem(burger)}
                />
            );
            if (Object.keys(orderItems).length !== 0) {
                burgerComponents.push(
                    <div>
                        <h2>Pedido</h2>
                        {
                            Object.entries(orderItems).map(entry =>
                            <OrderItem
                                key={entry[0]}
                                id={entry[0]}
                                burger={entry[1]}
                                ingredients={this.state.ingredients}
                                remove={() => this.removeOrderItem(entry[0])}
                                update={(id, burger) => this.updateOrder(id, burger)}
                            />)
                        }
                        <button onClick={() => this.submitOrder()}>Gerar Pedido</button>
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