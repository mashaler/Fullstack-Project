import React, { Component } from 'react';
import { Card, CardActionArea, CardContent } from '@material-ui/core';
import './css/ItemCard.css';

class ItemCard extends Component {
    state = {};
    render() {
        const name = this.props.itemName;
        const price = this.props.itemPrice;
        const volume = this.props.itemVolume;
        const id = this.props.itemId;

        return (
            <Card id='Item-Card'>
                <CardActionArea id='BuyButton' onClick={() => this.props.onSelect(id, price, volume)}>
                    <span id='Item-Id'>{id}</span>
                    <Card id='Item-Name'>
                        <CardContent>
                            <span>{name}</span>
                        </CardContent>
                    </Card>
                    <br />
                    <span id='Item-Price'>${price}</span>
                    <br />
                    <br />
                    <Card id='Item-Info'>
                        <CardContent>
                            <span id='Item-Volume'>Left in Stock: {volume}</span>
                            <br />
                        </CardContent>
                    </Card>
                </CardActionArea>
            </Card>
        );
    }
}

export default ItemCard;
