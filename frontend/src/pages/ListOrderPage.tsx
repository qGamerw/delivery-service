import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Card } from 'antd';
import orderService from '../services/orderService';
import { RootState } from '../store';

const ListOrderPage: React.FC = () => {
    const dispatch = useDispatch();
    const allOrders = useSelector((store: RootState) => store.order.allOrders);

    useEffect(() => {
        orderService.getAwaitingDeliveryOrders(dispatch);
    }, []);

    return (
        <>
            {allOrders.map((order) => (
                <Card title={`Order ID: ${order.id}`} key={order.id} style={{ marginBottom: '16px' }}>
                    <p>Имя клиента: {order.clientName}</p>
                    <p>Номер клиента: {order.clientPhone}</p>
                    <p>Описание: {order.description}</p>
                    <p>Статус: {order.eStatusOrders}</p>
                    <p>Доставить до: {new Date(order.orderTime).toLocaleString('ru-RU')}</p>
                    <p>Адрес клиента: {order.address}</p>
                    <p>Адрес ресторана: {order.branchAddress}</p>
                    <p>Квартира: {order.flat}</p>
                    <p>Подъезд: {order.frontDoor}</p>
                    <p>Этаж: {order.floor}</p>
                    <p>Вес: {order.weight}</p>
                    <p>Время готовки: {order.orderTime ? new Date(order.endCookingTime).toLocaleString('ru-RU'): 'Нет данных'}</p>
                    <p>Состав: {order.dishesOrders.map((dish) => dish.dishName).join(', ').toLowerCase()}</p>
                </Card>
            ))}
        </>
    );
};

export default ListOrderPage;
