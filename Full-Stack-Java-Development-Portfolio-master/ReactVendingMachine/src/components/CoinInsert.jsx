import React, { Component } from 'react';
import { Grid, Card, CardContent, CardActionArea } from '@material-ui/core';
import './css/CoinInsert.css';

class CoinInert extends Component {
    state = {};

    render() {
        return (
            <React.Fragment>
                <Card id='Coint-Insert'>
                    <CardContent>
                        <Card>
                            <CardContent id='Coin-Insert-Header'>Total Funds:</CardContent>
                            <CardContent id='User-Funds-Display'>{this.props.currentFunds.toFixed(2)}</CardContent>
                        </Card>
                    </CardContent>
                    <Grid container spacing={0} id='Button-Grid'>
                        <Grid item xs={6} className='Add-Coin'>
                            <CardActionArea
                                className='Coin-Button'
                                onClick={() => {
                                    this.props.onAddDollar();
                                    this.props.onResetMessage();
                                }}
                            >
                                Add Dollar
                            </CardActionArea>
                        </Grid>
                        <Grid item xs={6} className='Add-Coin'>
                            <CardActionArea
                                className='Coin-Button'
                                onClick={() => {
                                    this.props.onAddQuarter();
                                    this.props.onResetMessage();
                                }}
                            >
                                Add Quarter
                            </CardActionArea>
                        </Grid>
                        <Grid item xs={6} className='Add-Coin'>
                            <CardActionArea
                                className='Coin-Button'
                                onClick={() => {
                                    this.props.onAddDime();
                                    this.props.onResetMessage();
                                }}
                            >
                                Add Dime
                            </CardActionArea>
                        </Grid>
                        <Grid item xs={6} className='Add-Coin'>
                            <CardActionArea
                                className='Coin-Button'
                                onClick={() => {
                                    this.props.onAddNickle();
                                    this.props.onResetMessage();
                                }}
                            >
                                Add Nickel
                            </CardActionArea>
                        </Grid>
                    </Grid>
                </Card>
            </React.Fragment>
        );
    }
}

export default CoinInert;
