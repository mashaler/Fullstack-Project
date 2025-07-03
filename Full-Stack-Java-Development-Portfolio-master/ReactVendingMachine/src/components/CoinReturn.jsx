import React, { Component } from 'react';
import { Card, CardContent, CardActionArea } from '@material-ui/core';
import './css/CoinReturn.css';
class CointReturn extends Component {
    state = {
        change: null,
    };

    render() {
        return (
            <Card id='CoinReturn'>
                <CardContent>
                    <Card>
                        <CardContent id='Coin-Return-Header'>Change:</CardContent>
                        <CardContent id='Coin-Return-Content'>{this.props.changeString}</CardContent>
                    </Card>
                </CardContent>
                <CardActionArea id='Coin-Return-Button' onClick={this.props.onGetChange}>
                    Get Change
                </CardActionArea>
            </Card>
        );
    }
}

export default CointReturn;
