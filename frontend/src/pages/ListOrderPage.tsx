import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Card, Button, Collapse, Pagination } from 'antd';
import styled from 'styled-components';
import orderService from '../services/orderService';
import { RootState } from '../store';

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

const PaginationWrapper = styled.div`
  margin-top: 16px;
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
    const dispatch = useDispatch();
    const allOrders = useSelector((store: RootState) => store.order.allOrders);
    const user = useSelector((store: RootState) => store.user.currentUser);

    const [currentPage, setCurrentPage] = useState(1);
    const ordersPerPage = 5;
    const indexOfLastOrder = currentPage * ordersPerPage;
    const indexOfFirstOrder = indexOfLastOrder - ordersPerPage;
    const currentOrders = allOrders.slice(indexOfFirstOrder, indexOfLastOrder);

    useEffect(() => {
        orderService.getAwaitingDeliveryOrders(dispatch);
    }, []);

    const [expandedPanel, setExpandedPanel] = useState('');

    const handleCollapseChange = (key: string | string[]) => {
        if (Array.isArray(key)) {
            setExpandedPanel(key[1]);
        } else {
            setExpandedPanel(key);
        }
    };

    const handlePageChange = (page: number) => {
        setCurrentPage(page);
    };

    const handleClickAcceptOrder = (idOrder:number) => {
        console.log(user);
        if (user) {

            orderService.assignOrderToCourier({id:idOrder, courierId:user?.id}, dispatch);
        }
    };

    return (
        <Container>
            <CardsContainer>
                {currentOrders.map((order) => (
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
                                        <strong>Status:</strong> {order.estatusOrders}
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
                        <div style={{ display: 'flex', justifyContent: 'center', marginTop: '16px' }}>
                            <Button type="primary" style={{ width: '100%' }} onClick={() => handleClickAcceptOrder(order.id)}>
                                Accept Order
                            </Button>
                        </div>
                    </StyledCard>
                ))}
            </CardsContainer>
            <PaginationWrapper>
                <Pagination
                    current={currentPage}
                    total={allOrders.length}
                    defaultPageSize={ordersPerPage}
                    onChange={handlePageChange}
                />
            </PaginationWrapper>
        </Container>
    );
};

export default ListOrderPage;