import React, { Component } from 'react';
import { Card, CardContent, CardActionArea } from '@material-ui/core';
import './css/MessageDisplay.css';

class MessageDisplay extends Component {
    state = {};
    render() {
        return (
            <React.Fragment>
                <Card id='MessageDisplay'>
                    <CardContent>
                        <Card>
                            <CardContent id='Message-Display-Header'>Message:</CardContent>
                            <CardContent id='Message-Display-Content'>{this.props.responseMessage}</CardContent>
                        </Card>
                        <br />
                        <Card>
                            <CardContent id='Selected-Item-Id'>Selected Item: {this.props.selectedItem.selectedItemId}</CardContent>
                            <CardActionArea id='Purchase-Item-Button' onClick={this.props.onPuchaseItem}>
                                Purchase Item
                            </CardActionArea>
                        </Card>
                    </CardContent>
                </Card>
            </React.Fragment>
        );
    }
}

export default MessageDisplay;
