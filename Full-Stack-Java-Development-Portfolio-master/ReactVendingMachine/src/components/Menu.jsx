import React, { Component } from 'react';
import { Grid } from '@material-ui/core';
import ItemCard from './ItemCard';
import './css/Menu.css';

class Menu extends Component {
    state = {};

    render() {
        const { items, onSelect } = this.props;

        return (
            <div id='ItemGrid'>
                <header id='Header'>Choose Your Item:</header>
                <br></br>
                <Grid container spacing={10}>
                    {items.map(item => (
                        <Grid item sm={12} lg={6} xl={4} key={item.id}>
                            <ItemCard key={item.id} itemName={item.name} itemPrice={item.price} itemVolume={item.quantity} itemId={item.id} onSelect={onSelect} />
                        </Grid>
                    ))}
                </Grid>
            </div>
        );
    }
}

export default Menu;
