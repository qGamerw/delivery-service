import React, { useState, useEffect } from 'react';
import { Button, Result } from 'antd';
import { Link } from "react-router-dom";
import { AndroidOutlined } from '@ant-design/icons';


const NotFoundPage: React.FC = () => {
    const [konamiCode, setKonamiCode] = useState<string[]>([]);
    const konamiSequence = ['ArrowUp', 'ArrowUp', 'ArrowDown', 'ArrowDown', 'ArrowLeft', 'ArrowRight', 'ArrowLeft', 'ArrowRight', 'b', 'a'];
    const [konamiCodeEntered, setKonamiCodeEntered] = useState(false);

    useEffect(() => {
      const handleKeyPress = (e: KeyboardEvent) => {
        const updatedCode = [...konamiCode, e.key];
        if (updatedCode.length > konamiSequence.length) {
          updatedCode.shift();
        }
  
        setKonamiCode(updatedCode);
  
        if (JSON.stringify(updatedCode) === JSON.stringify(konamiSequence)) {
          setKonamiCodeEntered(true);
        }
      };
  
      window.addEventListener('keydown', handleKeyPress);
  
      return () => {
        window.removeEventListener('keydown', handleKeyPress);
      };
    }, [konamiCode]);
  
    return (
      <Result
        status={konamiCodeEntered ? undefined : "404"}
        title="404"
        subTitle={
          konamiCodeEntered
            ? 'Похоже вы используете не мобильное устройство.'
            : 'К сожалению, страница, которую вы посетили, не существует.'
        }
        icon={konamiCodeEntered ? <AndroidOutlined /> : undefined}
        extra={
          <Button type="primary">
            <Link to="/">{konamiCodeEntered ? 'Может смените девайс?' : 'Назад на главную'}</Link>
          </Button>
        }
      />
    );
};

export default NotFoundPage;

