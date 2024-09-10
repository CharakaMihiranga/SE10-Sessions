import uuid
from sqlalchemy import Column, String, create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import validates, sessionmaker
import bcrypt

DATABASE_URL = 'mysql+pymysql://root:1234@localhost/se10sessions'
engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
Base = declarative_base()

class User(Base):
    __tablename__ = 'user'

    # Use String type for the ID to store UUIDs
    id = Column(String(36), primary_key=True, default=lambda: str(uuid.uuid4()))
    name = Column(String(255), index=True)
    email = Column(String(255), unique=True, index=True)
    password = Column(String(255))

    @staticmethod
    def hash_password(password):
        # Hash password before storing it
        if password:
            hashed = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())
            return hashed.decode('utf-8')
        return password

    def check_password(self, password):
        # Verify password
        return bcrypt.checkpw(password.encode('utf-8'), self.password.encode('utf-8'))

# Create tables
Base.metadata.create_all(bind=engine)
