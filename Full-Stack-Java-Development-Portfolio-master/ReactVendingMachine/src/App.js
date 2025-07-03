import React, { Component } from 'react';
import Menu from './components/Menu';
import logo from './logo.svg';
import MoneyHandler from './components/MoneyHandler';
import './App.css';

class App extends Component {
    state = {
        selectedItem: {
            selectedItemId: null,
            selectedItemPrice: null,
            selectedItemQuantity: null,
        },
        itemsFromService: [],
    };

    getItemsFromService = () => {
        const SERVICE_URL = 'http://tsg-vending.herokuapp.com';
        fetch(SERVICE_URL + '/items')
            .then(data => data.json())
            .then(data => this.setState({ itemsFromService: data }));
    };

    componentDidMount() {
        this.getItemsFromService();
    }

    handleSelectItem = (id, price, quantity) => {
        this.setState({
            selectedItem: {
                selectedItemId: id,
                selectedItemPrice: price,
                selectedItemQuantity: quantity,
            },
        });
    };

    render() {
        return (
            <div className='App'>
                <header className='App-header'>
                    <img src={logo} className='App-logo' alt='logo' />
                    <div className='Header-text'>A React Vending Machine</div>

                    <img src={logo} className='App-logo' alt='logo' />
                </header>
                <hr />
                <div id='Full-Display'>
                    <Menu items={this.state.itemsFromService} onSelect={this.handleSelectItem} />
                    <div style={{ borderLeft: '2px solid #fff', height: '1700px' }}></div>
                    <MoneyHandler selectedItem={this.state.selectedItem} onUpdate={this.getItemsFromService} />
                </div>
            </div>
        );
    }
}

export default App;
