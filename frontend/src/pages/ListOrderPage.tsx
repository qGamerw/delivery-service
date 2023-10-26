import React, { useEffect, useState, useRef } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Card, Button, Collapse, Spin } from 'antd';
import styled from 'styled-components';
import orderService from '../services/orderService';
import { RootState } from '../store';
import { setAllOrders } from '../slices/orderSlice';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const CardsContainer = styled.div`
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
`;

const StyledCard = styled(Card)`
  margin: 16px;
  width: 300px;
  border: 1px solid #d9d9d9;
  padding: 16px;
  transition: box-shadow 0.3s;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

const { Panel } = Collapse;

const ListOrderPage: React.FC = () => {
    const [loading, setLoading] = useState(true);
    const [expandedPanel, setExpandedPanel] = useState('');

    const handleCollapseChange = (key: string | string[]) => {
        if (Array.isArray(key)) {
            setExpandedPanel(key[1]);
        } else {
            setExpandedPanel(key);
        }
    };

    const handleClickAcceptOrder = (idOrder:number) => {
        console.log(user);
        if (user) {

            orderService.assignOrderToCourier({id:idOrder, courierId:user?.id}, dispatch);
        }
    };


    const dispatch = useDispatch();
    const allOrders = useSelector((store: RootState) => store.order.allOrders);
    const user = useSelector((store: RootState) => store.auth.user);

    const [currentPage, setCurrentPage] = useState(1);
    const isLoading = useRef(false);

    const loadMoreOrders = () => {
        if (!isLoading.current) {
            isLoading.current = true;
            orderService.getAwaitingDeliveryOrders(currentPage - 1, dispatch).then((newOrders) => {
                if (newOrders.length > 0) {
                    setCurrentPage((prevPage) => prevPage + 1);
                } else if (newOrders.length === 0) {
                    setLoading(false);
                }
                isLoading.current = false;
            });
        }
    };

    useEffect(() => {
        dispatch(setAllOrders([]));
        loadMoreOrders();
    }, []);

    const handleScroll = () => {
        if (
            window.innerHeight + document.documentElement.scrollTop ===
            document.documentElement.offsetHeight
        ) {
            loadMoreOrders();
        }
    };

    useEffect(() => {
        window.addEventListener('scroll', handleScroll);
        return () => window.removeEventListener('scroll', handleScroll);
    }, [currentPage]);



    return (
        <Container>
            <CardsContainer>
                {allOrders.map((order) => (
                    <StyledCard title={`Order № ${order.id}`} key={order.id}>
                        <div>
                            <p>
                                <strong>Cooking Time:</strong> {order.orderTime
                                ? new Date(order.endCookingTime).toLocaleString('ru-RU')
                                : 'No data'}
                            </p>
                            <p>
                                <strong>Delivery Time:</strong>{' '}
                                {new Date(order.orderTime).toLocaleString('ru-RU')}
                            </p>
                            <p>
                                <strong>Weight:</strong> {order.weight}
                            </p>
                            <p>
                                <strong>Customer Address:</strong> {order.address}
                            </p>
                            <p>
                                <strong>Restaurant Address:</strong> {order.branchAddress}
                            </p>
                            <Collapse activeKey={expandedPanel} onChange={handleCollapseChange}>
                                <Panel header="Show more" key={order.id}>
                                    <p>
                                        <strong>Customer Name:</strong> {order.clientName}
                                    </p>
                                    <p>
                                        <strong>Customer Number:</strong> {order.clientPhone}
                                    </p>
                                    <p>
                                        <strong>Description:</strong> {order.description}
                                    </p>
                                    <p>
                                        <strong>Status:</strong> {order.status}
                                    </p>
                                    <p>
                                        <strong>Apartment, Entrance, and Floor:</strong> {order.flat},{' '}
                                        {order.frontDoor}, {order.floor}
                                    </p>
                                    <p>
                                        <strong>Composition:</strong>{' '}
                                        {order.dishesOrders.map((dish) => dish.dishName).join(', ').toLowerCase()}
                                    </p>
                                </Panel>
                            </Collapse>
                        </div>
                        <div
                            style={{ display: 'flex', justifyContent: 'center', marginTop: '16px' }}
                        >
                            <Button
                                type="primary"
                                style={{ width: '100%' }}
                                onClick={() => handleClickAcceptOrder(order.id)}
                            >
                                Accept Order
                            </Button>
                        </div>
                    </StyledCard>
                ))}
                {loading && (
                    <Spin tip="Loading..." size="large" style={{ margin: '16px auto' }} />
                )}
            </CardsContainer>
        </Container>
    );
};


export default ListOrderPage;