import React, { Component } from 'react';
import { Card, CardContent } from '@material-ui/core';
import CoinInsert from './CoinInsert';
import './css/MoneyHandler.css';
import MessageDisplay from './MessageDisplay';
import CoinReturn from './CoinReturn';

class MoneyHandler extends Component {
    state = {
        currentFunds: 0,
        responseMessage: 'Please Make a Selection',
        change: { quarters: 0, dimes: 0, nickels: 0, pennies: 0 },
        changeString: '',
    };

    handleAddDollar = () => {
        this.setState({ currentFunds: this.state.currentFunds + 1 });
    };

    handleAddQuarter = () => {
        this.setState({ currentFunds: this.state.currentFunds + 0.25 });
    };

    handleAddDime = () => {
        this.setState({ currentFunds: this.state.currentFunds + 0.1 });
    };

    handleAddNickel = () => {
        this.setState({ currentFunds: this.state.currentFunds + 0.05 });
    };

    handleResetMessage = () => {
        this.setState({ responseMessage: 'Please Make a Selection' });
    };

    handleBuyItem = () => {
        const URL_BODY = 'http://tsg-vending.herokuapp.com/money/';

        let funds = this.state.currentFunds.toFixed(2);
        let id = this.props.selectedItem.selectedItemId;

        if (id != null) {
            fetch(URL_BODY + funds + '/item/' + id, {
                method: 'POST',
                body: JSON.stringify(this.state.change),
            }).then(response => {
                if (response.status === 200) {
                    response.json().then(data => {
                        this.setState({ change: data });
                        this.formatChange();
                        this.props.onUpdate();
                        this.setState({ currentFunds: 0 });
                        this.setState({ responseMessage: 'Purchase Successful' });
                    });
                } else {
                    response.json().then(data => this.setState({ responseMessage: data.message }));
                }
            });
        }
    };

    formatChange = () => {
        const change = this.state.change;
        const coinTypes = Object.keys(change);
        let changeString = '';
        for (let i = 0; i < coinTypes.length; i++) {
            if (change[coinTypes[i]] !== 0) {
                changeString += coinTypes[i] + ': ' + change[coinTypes[i]] + ' ';
            }
        }

        this.setState({ changeString: changeString });
        this.setState({ change: null });
        this.setState({ currentFunds: 0 });
    };

    manuallyDetermineChange = () => {
        let funds = this.state.currentFunds;
        const coinValues = [0.25, 0.1, 0.05, 0.01];
        let changeArray = [0, 0, 0, 0];
        if (funds === 0) {
            this.setState({ responseMessage: 'Please Insert Funds' });
            this.setState({ changeString: null });
        } else {
            for (let i = 0; i < coinValues.length; i++) {
                while ((funds % coinValues[i]).toFixed(0) == 0 && funds - coinValues[i] >= 0) {
                    changeArray[i] += 1;
                    funds = (funds - coinValues[i]).toFixed(2);
                }
            }
            this.setState(
                {
                    change: {
                        quarters: changeArray[0],
                        dimes: changeArray[1],
                        nickels: changeArray[2],
                        pennies: changeArray[3],
                    },
                },
                () => {
                    this.formatChange();
                }
            );
        }
    };

    render() {
        return (
            <React.Fragment>
                <Card id='MoneyHandler-UI'>
                    <CardContent>
                        <CoinInsert
                            onAddDollar={this.handleAddDollar}
                            onAddQuarter={this.handleAddQuarter}
                            onAddDime={this.handleAddDime}
                            onAddNickle={this.handleAddNickel}
                            currentFunds={this.state.currentFunds}
                            onResetMessage={this.handleResetMessage}
                        />
                        <br></br>
                        <MessageDisplay selectedItem={this.props.selectedItem} responseMessage={this.state.responseMessage} onPuchaseItem={this.handleBuyItem} />
                        <br></br>
                        <CoinReturn changeString={this.state.changeString} onGetChange={this.manuallyDetermineChange} />
                    </CardContent>
                </Card>
            </React.Fragment>
        );
    }
}

export default MoneyHandler;
